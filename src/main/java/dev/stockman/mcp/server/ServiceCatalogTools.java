package dev.stockman.mcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ServiceCatalogTools {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogTools.class);
    private static final Map<String, ServiceInfo> catalog = new ConcurrentHashMap<>();

    static {
        catalog.put("payment-service", new ServiceInfo("Handles payment processing", "ACTIVE"));
        catalog.put("user-service", new ServiceInfo("Manages user profiles", "ACTIVE"));
        catalog.put("notification-service", new ServiceInfo("Sends emails and SMS", "MAINTENANCE"));
    }

    record ServiceInfo(String description, String status) {}

    @Tool(description = "List available services in the catalog")
    public List<String> list() {
        logger.info("📋 Listing all services");
        return List.copyOf(catalog.keySet());
    }

    @Tool(description = "Get details about a specific service by name")
    public String getDetails(@ToolParam(description = "The name of the service to retrieve details for") String serviceName) {
        logger.info("🔍 Getting details for service: {}", serviceName);
        ServiceInfo info = catalog.get(serviceName);
        if (info == null) {
            return "Service not found: " + serviceName;
        }
        return "Service: " + serviceName + ", Description: " + info.description + ", Status: " + info.status;
    }

    @Tool(description = "Register a new service in the catalog")
    public String register(
            @ToolParam(description = "Unique name for the new service") String serviceName,
            @ToolParam(description = "Brief description of what the service does") String description) {
        logger.info("➕ Registering new service: {}", serviceName);
        if (catalog.containsKey(serviceName)) {
            return "Service already exists: " + serviceName;
        }
        catalog.put(serviceName, new ServiceInfo(description, "DRAFT"));
        return "Successfully registered service: " + serviceName;
    }

    @Tool(description = "Update the status of an existing service")
    public String updateStatus(
            @ToolParam(description = "The name of the service to update") String serviceName,
            @ToolParam(description = "The new status (e.g., ACTIVE, MAINTENANCE, DEPRECATED)") String newStatus) {
        logger.info("🔄 Updating status for service: {} to {}", serviceName, newStatus);
        if (!catalog.containsKey(serviceName)) {
            return "Service not found: " + serviceName;
        }
        catalog.computeIfPresent(serviceName, (_, current) -> new ServiceInfo(current.description, newStatus));
        return "Updated status of " + serviceName + " to " + newStatus;
    }

    @Tool(description = "Remove a service from the catalog")
    public String unregister(@ToolParam(description = "The name of the service to remove") String serviceName) {
        logger.info("❌ Unregistering service: {}", serviceName);
        if (catalog.remove(serviceName) != null) {
            return "Removed service: " + serviceName;
        }
        return "Service not found: " + serviceName;
    }

}
