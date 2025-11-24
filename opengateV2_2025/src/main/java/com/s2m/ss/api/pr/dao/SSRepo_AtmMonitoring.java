package com.s2m.ss.api.pr.dao;

import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s2m.ss.api.pr.config.SS_LoggingImpl;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AtmMonitoringRq;

import lombok.Getter;
import lombok.Setter;

public class SSRepo_AtmMonitoring implements SQLData {
	private final String SCHEMA_SQL = "OPENGATEV2";
    private final String PACKAGE_SQL = "opengate_acquirer";
    private final String PROCEDURE_SQL = "ATM_MONITORING";
    @Getter @Setter
    
    private SSEnt_AtmMonitoringRq request;
 
    @Getter @Setter
    private String response;
 
    private SS_LoggingImpl logr;
 
    public SSRepo_AtmMonitoring() {
        logr = new SS_LoggingImpl();
        logr.setClazz(getClass());
    }
 
    public SSRepo_AtmMonitoring(SSEnt_AtmMonitoringRq request) {
        this();
        this.request = request;
    }
    

 
    @Override
    public void readSQL(SQLInput stream, String typeName) {
        try {
            logr.getLog().trace("Start readSQL, atm monitoring ");
            response = stream.readString(); // Read output parameter
        } catch (NullPointerException e) {
            logr.getLog().trace("No data available in atm monitoring : " + e.getMessage());
        } catch (Exception e) {
            logr.getLog().error("Error in readSQL, atm monitoring ", e);
        }
    }
 
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        try {
            logr.getLog().trace("Start writeSQL, atm monitoring");
            stream.writeString(request != null ? request.toString() : null); 
            logr.getLog().trace("End writeSQL, atm monitoring");
        } catch (Exception e) {
            logr.getLog().error("Error in writeSQL, atm monitoring", e);
        }
    }
 
    @Override
    public String getSQLTypeName() {
        return "YOUR_TYPE_NAME"; 
    }
    /*
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String processing(DataSource dataSource) {
        String response = null;
        try {
            logr.getLog().trace("Start processing, atm monitoring");
            if (request == null) {
                logr.getLog().error("Request object is null.");
                return "{\"status\":\"error\",\"message\":\"Request object is null\"}";
            }
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(request);
            logr.getLog().trace("Serialized JSON request: " + jsonRequest);

           
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withSchemaName(SCHEMA_SQL)
                .withCatalogName(PACKAGE_SQL)
                .withProcedureName(PROCEDURE_SQL)
                .declareParameters(
                    new SqlParameter("jsonrequest", Types.VARCHAR), 
                    new SqlOutParameter("jsonresponse", Types.VARCHAR) 
                );

            
            Map<String, Object> inParams = Collections.singletonMap("jsonrequest", jsonRequest);
            logr.getLog().debug("Input parameters: " + inParams);

             Map<String, Object> outParams = jdbcCall.execute(inParams);
            response = (String) outParams.get("jsonresponse");
            if (response == null || response.isEmpty()) {
                logr.getLog().error("Procedure returned no response or an empty response.");
                return "{\"status\":\"error\",\"message\":\"Empty response from procedure\"}";
            }
            logr.getLog().trace("Response from procedure: " + response);
        } catch (Exception e) {
            logr.getLog().error("Error during procedure processing", e);
            return "{\"status\":\"error\",\"message\":\"Error during procedure execution\"}";
        }
        return response;
    }
*/
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String processing(DataSource dataSource) {
        String response = null;
        try {
            logr.getLog().trace("Start processing, atm monitoring");

            // Validate request
            if (request == null) {
                logr.getLog().error("Request object is null.");
                return "{\"status\":\"error\",\"message\":\"Request object is null\"}";
            }

            // Serialize request object to JSON
            ObjectMapper mapper = new ObjectMapper();
            String jsonRequest = mapper.writeValueAsString(request);
            logr.getLog().trace("Serialized JSON request: " + jsonRequest);

            // Prepare JDBC call
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withSchemaName(SCHEMA_SQL)
                    .withCatalogName(PACKAGE_SQL)
                    .withProcedureName(PROCEDURE_SQL)
                    .declareParameters(
                            new SqlParameter("jsonrequest", Types.VARCHAR),
                            new SqlOutParameter("jsonresponse", Types.CLOB) // <-- CLOB here
                    );

            Map<String, Object> inParams = Collections.singletonMap("jsonrequest", jsonRequest);
            logr.getLog().debug("Input parameters: " + inParams);

            // Execute procedure
            Map<String, Object> outParams = jdbcCall.execute(inParams);
            Clob clobResponse = (Clob) outParams.get("jsonresponse");

            // Read CLOB safely
            if (clobResponse != null) {
                try (Reader reader = clobResponse.getCharacterStream()) {
                    StringBuilder sb = new StringBuilder();
                    char[] buffer = new char[1024];
                    int bytesRead;
                    while ((bytesRead = reader.read(buffer)) != -1) {
                        sb.append(buffer, 0, bytesRead);
                    }
                    response = sb.toString();
                }
            }

            // Validate response
            if (response == null || response.isEmpty()) {
                logr.getLog().error("Procedure returned no response or an empty response.");
                return "{\"status\":\"error\",\"message\":\"Empty response from procedure\"}";
            }

            logr.getLog().trace("Response from procedure length: " + response.length());
        } catch (Exception e) {
            logr.getLog().error("Error during procedure processing", e);
            return "{\"status\":\"error\",\"message\":\"Error during procedure execution\"}";
        }

        return response;
    }

}
