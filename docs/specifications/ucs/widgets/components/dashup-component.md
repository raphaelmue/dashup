# API Reference: DashupComponent - V1.0

## Inheritance
N.A.

##Properties
**Name**
        
        Type: String
        Description: Unique name of the component
        
**Layout**

        Type: Object
        Description: Describes where the component should be places inside the widget
     
                <dashup-input ... >layout='{"row": 2, "offset": 1, "size": 3}'
                
        Row property is mandatory for the grid layout to work, whereas default offset is set to 0 and default size is 
        set to 4.