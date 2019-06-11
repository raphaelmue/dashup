# API Reference: DashupChart - V1.1

## Inheritance

Inherits from [DashupComponent](dashup-component.md).

## Interface

**title**
        
        Type: String
        Description: The name of the bar chart
        
**category**

        Type: String
        Desciption: Additional descriptive text for the displayed data.
        
## Data Format

        To fill the chart with data delivered from an API, the format must be compliant with the following:
        
        [
            {
                category: "...",
                value: 1234
            },
            ...
        ]
        
## Attention

        The chart component is design to handle data with bigger numbers, otherwise the scaling of the chart will be 
        missleading in the current state.