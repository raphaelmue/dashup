# API Reference: DashupChart - V1.0

## Inheritance

Inherits from [DashupComponent](dashup-component.md).

##Interface
**Title**
        
        Type: String
        Description: The name of the bar chart
        
**Category**

        Type: String
        Desciption: Additional descriptive text for the displayed data.
        
**Data**

        Type: Array
        Description: Dataset to display in the chart. Format must be compliant to the following:
        
            <dashup-chart name="myChart" title="finance chart" category="my spendings"
                          data='[{"category": "family", "value": "3000"}, {"category": "car", "value": "150"}]' ...>