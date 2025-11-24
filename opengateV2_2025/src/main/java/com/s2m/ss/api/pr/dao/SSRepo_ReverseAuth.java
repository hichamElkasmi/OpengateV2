package com.s2m.ss.api.pr.dao;

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
import com.s2m.ss.api.pr.entities.requests.SSEnt_ReverseAuthRq;

import lombok.Getter;
import lombok.Setter;

public class SSRepo_ReverseAuth implements SQLData{

	private final String SCHEMA_SQL = "OPENGATEV2";
    private final String PACKAGE_SQL = "wt_interface_pkg";
    private final String PROCEDURE_SQL = "reverse_auth";
    @Getter @Setter
    
    private SSEnt_ReverseAuthRq request;
 
    @Getter @Setter
    private String response;
 
    private SS_LoggingImpl logr;
 
    public SSRepo_ReverseAuth() {
        logr = new SS_LoggingImpl();
        logr.setClazz(getClass());
    }
 
    public SSRepo_ReverseAuth(SSEnt_ReverseAuthRq request) {
        this();
        this.request = request;
    }
    

 
    @Override
    public void readSQL(SQLInput stream, String typeName) {
        try {
            logr.getLog().trace("Start readSQL, reversal authorization  ");
            response = stream.readString(); // Read output parameter
        } catch (NullPointerException e) {
            logr.getLog().trace("No data available in reversal authorization : " + e.getMessage());
        } catch (Exception e) {
            logr.getLog().error("Error in readSQL, reversal authorization ", e);
        }
    }
 
    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        try {
            logr.getLog().trace("Start writeSQL, reversal authorization  ");
            stream.writeString(request != null ? request.toString() : null); 
            logr.getLog().trace("End writeSQL, reversal authorization  status");
        } catch (Exception e) {
            logr.getLog().error("Error in writeSQL, reversal authorization ", e);
        }
    }
 
    @Override
    public String getSQLTypeName() {
        return "YOUR_TYPE_NAME"; 
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String processing(DataSource dataSource) {
        String response = null;
        try {
            logr.getLog().trace("Start processing, reversal authorization ");
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
                    new SqlParameter("v_request", Types.VARCHAR), 
                    new SqlOutParameter("v_response", Types.VARCHAR) 
                );

            
            Map<String, Object> inParams = Collections.singletonMap("v_request", jsonRequest);
            logr.getLog().debug("Input parameters: " + inParams);

             Map<String, Object> outParams = jdbcCall.execute(inParams);
            response = (String) outParams.get("v_response");
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
}
