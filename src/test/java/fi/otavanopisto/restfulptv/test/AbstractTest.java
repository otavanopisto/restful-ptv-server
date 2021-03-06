package fi.otavanopisto.restfulptv.test;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Abstract base class for all tests
 * 
 * @author Heikki Kurhinen
 * @author Antti Leppä
 */
public abstract class AbstractTest {
    
  private static Logger logger = Logger.getLogger(AbstractTest.class.getName());
  
  protected ZonedDateTime getZonedDateTime(int year, int month, int dayOfMonth, int hour, int minute, ZoneId zone) {
    return ZonedDateTime.of(year, month, dayOfMonth, hour, minute, 0, 0, zone);
  }
  
  protected OffsetDateTime getOffsetDateTime(int year, int month, int dayOfMonth, int hour, int minute, ZoneId zone) {
    return getZonedDateTime(year, month, dayOfMonth, hour, minute, zone).toOffsetDateTime();
  }
  
  protected Instant getInstant(int year, int month, int dayOfMonth, int hour, int minute, ZoneId zone) {
    return getOffsetDateTime(year, month, dayOfMonth, hour, minute, zone).toInstant();
  }
  
  protected int getHttpPort() {
    return Integer.parseInt(System.getProperty("it.port.http"));
  }

  protected String getHost() {
    return System.getProperty("it.host");
  }
  
  protected int getWireMockPort() {
    return  Integer.parseInt(System.getProperty("it.port.wiremock"));
  }
  
  protected String getWireMockBasePath() {
    return String.format("http://%s:%d", getHost(), getWireMockPort());
  }

  protected String getApiBasePath() {
    return String.format("http://%s:%d/v1", getHost(), getHttpPort());
  }
  
  protected long insertOrganizationSetting(String organizationId, String key, String value) {
    return executeInsert("insert into OrganizationSetting (settingKey, organizationKuntaApiId, value) values (?, ?, ?)", key, organizationId, value);
  }
  
  protected void deleteOrganizationSetting(String organizationId, String key) {
    executeDelete("delete from OrganizationSetting where settingKey = ? and organizationKuntaApiId = ?", key, organizationId);
  }
  
  protected long createIdentifier(String kuntaApiId, String source, String sourceId, String type) {
    return executeInsert("insert into Identifier (kuntaApiId, source, sourceId, type) values (?, ?, ?, ?)", 
      kuntaApiId, source, sourceId, type);
  }

  protected void deleteIndentifier(String key) {
    executeDelete("delete from Identifier where kuntaApiId = ?", key);
  }
  
  protected long executeInsert(String sql, Object... params) {
    try (Connection connection = getConnection()) {
      connection.setAutoCommit(true);
      PreparedStatement statement = connection.prepareStatement(sql);
      try {
        for (int i = 0, l = params.length; i < l; i++) {
          statement.setObject(i + 1, params[i]);
        }

        statement.execute();
        
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          return getGeneratedKey(generatedKeys);
        }
      } finally {
        statement.close();
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to execute insert", e);
      fail(e.getMessage());
    }
    
    return -1;
  }
  
  private long getGeneratedKey(ResultSet generatedKeys) throws SQLException {
    if (generatedKeys.next()) {
      return generatedKeys.getLong(1);
    }
    
    return -1;
  }
  
  protected void executeDelete(String sql, Object... params) {
    try (Connection connection = getConnection()) {
      connection.setAutoCommit(true);
      PreparedStatement statement = connection.prepareStatement(sql);
      try {
        for (int i = 0, l = params.length; i < l; i++) {
          statement.setObject(i + 1, params[i]);
        }

        statement.execute();
      } finally {
        statement.close();
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to execute delete", e);
      fail(e.getMessage());
    }
  }

  protected Connection getConnection() {
    String username = System.getProperty("it.jdbc.username");
    String password = System.getProperty("it.jdbc.password");
    String url = System.getProperty("it.jdbc.url");
    try {
      Class.forName(System.getProperty("it.jdbc.driver")).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      logger.log(Level.SEVERE, "Failed to load JDBC driver", e);
      fail(e.getMessage());
    }

    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Failed to get connection", e);
      fail(e.getMessage());
    }
    
    return null;
  }

  @SuppressWarnings ({"squid:S1188", "squid:MethodCyclomaticComplexity"})
  protected static Matcher<Instant> sameInstant(final Instant instant) {
    
    return new BaseMatcher<Instant>(){

      @Override
      public void describeTo(Description description) {
        description.appendText("same instant: ").appendValue(instant.toString());
      }

      @Override
      public boolean matches(Object item) {
        if (item == null && instant == null) {
          return true;
        }
        
        if (item == null || instant == null) {
          return false;
        }
        
        Instant itemInstant = toInstant(item);
        if (itemInstant == null) {
          return false;
        }
        
        return itemInstant.getEpochSecond() == instant.getEpochSecond();
      }
      
      private Instant toInstant(Object item) {
        if (item instanceof String) {
          return Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse((String) item));
        } else if (item instanceof Instant) {
          return (Instant) item;
        }
        
        return null;
      }
      
    };
    
  }
}
