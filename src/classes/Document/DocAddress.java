package classes.Document;

/**
 * This class is used, when we create copies of some documents
 * Here we have information, where we can find copy(room and level)
 *
 */
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

    @Override
    public String toString() {
        return "Room: " + room + ", level: " + level + ", DocCase: " + docCase;
    }
}