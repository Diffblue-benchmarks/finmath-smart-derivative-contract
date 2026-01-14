package net.finmath.smartcontract.valuation.service.controllers;

import com.diffblue.cover.annotations.InterestingTestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Custom base class for Diffblue Cover generated tests for PlainSwapEditorController.
 * Provides factory methods to create test instances with properly initialized SecurityContext.
 */
public abstract class PlainSwapEditorControllerDiffblueBase {

    /**
     * Minimal DatabaseConnector stub that does nothing.
     * Avoids mocks to prevent unused stubbing errors.
     */
    private static class MinimalDatabaseConnector extends net.finmath.smartcontract.valuation.marketdata.database.DatabaseConnector {
        public MinimalDatabaseConnector() {
            super(null);
        }

        @Override
        public void updateDatabase() {
            // No-op for testing
        }

        @Override
        public void fetchFromDatabase(java.util.List<String> fixingSymbols, String username) {
            // No-op for testing
        }
    }

    /**
     * Minimal ResourceGovernor stub that provides basic resource access.
     * Avoids mocks to prevent unused stubbing errors.
     */
    private static class MinimalResourceGovernor extends net.finmath.smartcontract.valuation.service.utils.ResourceGovernor {
        public MinimalResourceGovernor() {
            super(new PathMatchingResourcePatternResolver());
        }
    }

    /**
     * Factory method to create a PlainSwapEditorController with a properly initialized SecurityContext.
     * This prevents NullPointerException when testing methods that call SecurityContextHolder.getContext().getAuthentication().
     *
     * The SecurityContext is set up with a real Authentication object that returns a valid UserDetails principal.
     * This allows methods like getParRate(), getSavedContracts(), getSavedMarketData(), grabMarketData(),
     * and loadContract() to execute without throwing NullPointerException when accessing the principal.
     *
     * This factory avoids using mocks to prevent unused stubbing errors.
     *
     * @return A PlainSwapEditorController instance with SecurityContext properly initialized
     */
    @InterestingTestFactory
    public static PlainSwapEditorController createPlainSwapEditorControllerWithSecurityContext() {
        // Create a UserDetails object with a test username
        UserDetails userDetails = User.builder()
            .username("testuser")
            .password("testpass")
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
            .build();

        // Create an Authentication object with the UserDetails as principal
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            "testpass",
            userDetails.getAuthorities()
        );

        // Create a real SecurityContext and set the Authentication
        SecurityContext securityContext = new SecurityContextImpl(authentication);

        // Set the SecurityContext in SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        // Create minimal stub implementations to avoid mocks
        MinimalDatabaseConnector databaseConnector = new MinimalDatabaseConnector();
        MinimalResourceGovernor resourceGovernor = new MinimalResourceGovernor();
        ObjectMapper objectMapper = new ObjectMapper();

        // Create ValuationConfig with basic configuration
        ValuationConfig valuationConfig = new ValuationConfig();
        valuationConfig.setFpmlSchemaPath("classpath:schema/fpml-main-5-12.xsd");
        Map<String, String> providerToTemplate = new HashMap<>();
        providerToTemplate.put("test", "classpath:net/finmath/smartcontract/product/xml/smartderivativecontract.xml");
        valuationConfig.setMarketDataProviderToTemplate(providerToTemplate);

        // Create BuildProperties with minimal required properties
        Properties props = new Properties();
        props.setProperty("version", "1.0.0-TEST");
        props.setProperty("group", "net.finmath");
        props.setProperty("artifact", "finmath-smart-derivative-contract");
        props.setProperty("name", "finmath-smart-derivative-contract");
        props.setProperty("time", "2024-01-01T00:00:00Z");
        BuildProperties buildProperties = new BuildProperties(props);

        ResourceLoader resourceLoader = new DefaultResourceLoader();

        // Create and return the controller
        return new PlainSwapEditorController(
            databaseConnector,
            resourceGovernor,
            objectMapper,
            valuationConfig,
            valuationConfig,
            buildProperties,
            resourceLoader
        );
    }
}
