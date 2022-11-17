# Demo Project to receive messages from Azure Eventhubs using Kafka Transport with Managed Identity ( User Assigned Identity)

## **Create Azure Eventhubs Namespace & Azure Eventhub**
#### Create a resource group. Specify a name for the resource group.
az group create --name <resource group name> --location <Region Name>
#### Create an Event Hubs namespace. Specify a name for the Event Hubs namespace.
az eventhubs namespace create --name <Event Hubs namespace> --resource-group <resource group name> -l <region, for example: East US>
#### Create an event hub. Specify a name for the event hub.
az eventhubs eventhub create --name <event hub name> --resource-group <resource group name> --namespace-name <Event Hubs namespace>


## **User assigned Identity** 
#### Create Identity
az identity create -g myResourceGroup -n myUserAssignedIdentity
#### Assign Identity to Resource
az vm identity assign -g <VM RESOURCE GROUP> -n <VM NAME> --identities <USER ASSIGNED IDENTITY RESOURCE ID>
#### Assign Role to Identity
Identity can be granted "Azure Event Hubs Data owner" , "Azure Event Hubs Data sender" or "Azure Event Hubs Data receiver" Role <br />
spID=$(az resource list -n DevTestVMSS --query [*].identity.principalId --out tsv) <br />
az role assignment create --assignee $spID --role 'Azure Event Hubs Data receiver' --scope /subscriptions/<mySubscriptionID>/resourceGroups/<myResourceGroup>/providers/Microsoft.EventHub/namespaces/<Eventhub Namespace> <br />


#### Configurations
Update   https://github.com/SinglaSandeep/eventhubs/blob/main/managedIdentity/azure-eventhub-consumer-mi/src/main/resources/application.properties as per your project <br />
  EVENTHUB_NAMESPACE <br />
  TOPIC_NAME  <br />


##### Starting the service
git clone https://github.com/SinglaSandeep/eventhubs.git <br />
cd managedIdentity/azure-eventhub-producer-mi <br />
./gradlew build <br />
java -jar build/libs/azure-eventhub-consumer-mi-0.0.1-SNAPSHOT.jar <br />

Application will be started on port 8080

### References:
  **Creating Eventhub** <br />
    https://learn.microsoft.com/en-us/azure/event-hubs/event-hubs-quickstart-cli <br />
  **Assigning Identities** <br />
    https://learn.microsoft.com/en-us/azure/active-directory/managed-identities-azure-resources/qs-configure-cli-windows-vm <br />
    https://learn.microsoft.com/en-us/azure/active-directory/managed-identities-azure-resources/howto-assign-access-cli <br />
    https://learn.microsoft.com/en-us/azure/event-hubs/authorize-access-azure-active-directory?source=recommendations <br />
  **Managed Identities in Eventhub** <br />
    https://github.com/Azure/azure-event-hubs-for-kafka/tree/master/tutorials/oauth/java/managedidentity

  
