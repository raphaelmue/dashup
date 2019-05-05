# API Reference: DashupButton - V1.0

## Inheritance

Inherits from [DashupComponent](dashup-component.md).

##Interface
**Text**
        
        Type: String
        Description: The text displayed inside the button
        
**Mode**

        Type: Enum MessageMode
        Description: Type of action to be executed in the target component
     
**API**

        Type: String
        Description: URL address of the API to fetch or save data from/to. Dynamic URL parameters can be set as follows:
            
                https://www.my-api.de/main/%nameOne%?param=%nameTwo%
            
        The identifiers between the '%' serve as placeholders and must refer to a name of a component, whose value 
        should be inserted
            
**Params**

        Type: String
        Description: List of names of other components, whose values will fill the placeholders in the API URL.
        
                <dashup-button api="..." params="component1 component2" ...>  
   
**Consumers**

        Type: Object
        Description: The components displaying the data. The specification must obey to the following format:
        
                <dashup-button consumers='{"<component name>": "<path to data>"}'
                
        The path to the data follows the JSON format structure of the reponse returned from the API, for instance:
                
                apiData pupils 0 name
        
        The first indication must either be "apiData" for data returned from the API or the name of the component
        that produces data, like an dashup-text-input field.
   
**Producers**

        Type: String
        Description: The components delivering the data 
        
                <dashup-button producers="producer1 producer2" ...>  
        
**Disabled**

        Type: Boolean
        Description: Make the button not clickable