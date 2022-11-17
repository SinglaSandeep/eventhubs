# Demo Project to read  messages from Azure Eventhubs using Kafka Transport with Service Principal Authentication

## **Create Azure Eventhubs Namespace & Azure Eventhub**
#### Create a resource group. Specify a name for the resource group.
az group create --name <resource group name> --location <Region Name>
#### Create an Event Hubs namespace. Specify a name for the Event Hubs namespace.
az eventhubs namespace create --name <Event Hubs namespace> --resource-group <resource group name> -l <region, for example: East US>
#### Create an event hub. Specify a name for the event hub.
az eventhubs eventhub create --name <event hub name> --resource-group <resource group name> --namespace-name <Event Hubs namespace>


#### Create Service Principal & assign Eventhub Data Reader Role
az ad sp create-for-rbac --name myServicePrincipalName --role "Azure Event Hubs Data receiver" --scopes /subscriptions/<SUBID>>/resourceGroups/<RESOURCE_GROUP>>e/providers/Microsoft.EventHub/namespaces/<EVENTHUB_NAMESPACE> <br />

#### Configurations
Update https://github.com/SinglaSandeep/eventhubs/blob/main/azuread/azure-eventhub-consumer-azuread/src/main/resources/application.properties as per your project <br />
EVENTHUB_NAMESPACE <br />
TOPIC_NAME  <br />

Update https://github.com/SinglaSandeep/eventhubs/blob/main/azuread/azure-eventhub-consumer-azuread/src/main/java/com/azure/eventhub/authentication/CustomAuthenticateCallbackHandler.java  as per your project <br />
TENANT_ID <br />
SERVICE_PRINCIPAL_APPID  <br />
SERVICE_PRINCIPAL_SECRET <br />


### References:
**Creating Eventhub** <br />
https://learn.microsoft.com/en-us/azure/event-hubs/event-hubs-quickstart-cli <br />
**Creating Service Principal** <br />
https://learn.microsoft.com/en-us/cli/azure/create-an-azure-service-principal-azure-cli
https://learn.microsoft.com/en-us/azure/role-based-access-control/role-assignments-cli
https://learn.microsoft.com/en-us/azure/event-hubs/authorize-access-azure-active-directory?source=recommendations <br />
**Service Principal Authentication in Eventhub** <br />
https://github.com/Azure/azure-event-hubs-for-kafka/tree/master/tutorials/oauth/java/appsecret
  

      
  
      
  

  
