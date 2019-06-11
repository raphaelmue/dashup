export class MessageBroker {

    static MessageMode = {DISPLAY: "display", ADD: "add", DELETE: "delete"};

    static getDataFromPath(path, data) {
        if(path === ""){
            return data;
        }
        let accessors = path.split(" ");
        for (let i = 0; i < accessors.length; i++) {
            try {
                data = data[accessors[i]];
            } catch(exception){
                data = null;
            }
        }
        return data;
    }

}