# API Reference: DashupGridLayout - V1.0

## Inheritance

Inherits from [DashupComponent](dashup-component.md).

##Properties
**Elements**
    
        Type: Array (Set through child elements)
        Description: Stores the child elements of a grid layout, i.e. the components to put into the layout
            
                <grid-layout>
                    <component1 layout='{"row": 1, "offset": 0, "size": 2}' ...></component1>
                    <dashup-grid-layout layout='{"row": 2, "offset": 0, "size": 3}' ...></dashup-grid-layout>
                </grid-layout>