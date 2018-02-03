package classes.Document;

public class DocAddress {

    private String room;
    private String level;
    private String docCase;

    public DocAddress(String room, String level, String docCase) {
        setRoom(room);
        setLevel(level);
        setDocCase(docCase);
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDocCase() {
        return docCase;
    }

    public void setDocCase(String docCase) {
        this.docCase = docCase;
    }
}
