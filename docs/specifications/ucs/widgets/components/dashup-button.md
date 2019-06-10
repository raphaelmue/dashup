# API Reference: DashupButton - V1.1

## Inheritance

Inherits from [DashupComponent](dashup-component.md).

## Interface

**text**
        
        Type: String
        Description: The text displayed inside the button

**disabled**

        Type: Boolean
        Description: Makes the button not clickable
        
**handleOnStart**

        Type: Boolean
        Description: Triggers a button click during initialization process. Used to fetch initial data.
        
**mode**

        Type: Enum MessageMode
        Description: Type of action to be executed in the target component
        
        - "display": Deletes the displayed data and replaces it with the fetched data in the consumer widgets
        - "add": Adds the fetched data to the consumer widgets
        - "delete": Deletes the data from the consumer widgets, which are equal to the fetched data
     
**dataAPI**

        Type: String
        Description: URL address of the API to fetch data from. Dynamic URL parameters can be set as follows:
            
                https://www.my-api.de/main/%nameOne%?param=%nameTwo%
            
        The identifiers between the '%' serve as placeholders and must refer to a name of a component, whose value 
        should be inserted
            
**storageAPI**

        Type: String
        Description: URL address of the API to save data to. A dynamic adaption of the storage API is not possible. The 
                     API has to take care of routing data to the correct endpoint. 
            
**params**

        Type: String
        Description: List of names of other components, whose values will fill the placeholders in the dataAPI URL.
        
                <dashup-button api="..." params="component1 component2" ...>  
   
**consumers**

        Type: Array
        Description: The components displaying the data. The specification must obey to the following format:
        
                <dashup-button consumers='[{"component name": "path to data"},...]'>
                
        The path to the data follows the JSON format structure of the reponse returned from the API, for instance:
                
                apiData pupils 0 name
        
        The first indication can either be "apiData" for data returned from the API or the name of the component
        that produces data, like an dashup-text-input field.
   
**producers**

        Type: String
        Description: The components delivering the data 
        
                <dashup-button producers="producer1 producer2" ...>  
       
       Producers are, like consumers, referenced by the name given to the component tag.
       
## Attention

Combining inputs of multiple components to define one data object is not possible yet.