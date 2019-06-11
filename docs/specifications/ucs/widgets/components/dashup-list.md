# API Reference: DashupList - V1.1

## Inheritance

Inherits from [DashupComponent](dashup-component.md).

## Properties
      
**items**

        Type: Array
        Description: Elements of the list
        
            
        
**selectable**
    
        Type: Boolean
        Description: Make list items selectable
        
## Data Format

        To fill the list with data delivered from an API, the format must be compliant with the following:
        
        [
            {
                content: "...",
                selected: false
            },
            ...
        ]