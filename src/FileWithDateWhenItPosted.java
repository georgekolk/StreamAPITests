public class FileWithDateWhenItPosted {

    public int fileId;
    public String filePostDate;
    public String fileName;



    public FileWithDateWhenItPosted(String fileName, String filePostDate) {
        this.fileName = fileName;
        this.filePostDate = filePostDate;
    }

    public FileWithDateWhenItPosted(int fileId, String fileName, String filePostDate) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePostDate = filePostDate;
    }

    @Override
    public String toString() {
        return String.format("fileId: %s | fileName: %s | fileDate: %s ",
                this.fileId, this.fileName, this.filePostDate);
    }

}
