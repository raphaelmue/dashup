export class MessageBroker {

    static MessageMode = {DISPLAY: "display", ADD: "add", DELETE: "delete", SAVE: "save"};

    static getDataFromPath(path, data) {
        if(path === "")
            return data;
        let accessors = path.split(" ");
        for (let i = 0; i < accessors.length; i++) {
            data = data[accessors[i]];
        }
        return data;
    }

}