public class FileListWithCounter {


    public int filePostsCount;
    public String fileName;



    public FileListWithCounter(String fileName, int filePostsCount) {
        this.fileName = fileName;
        this.filePostsCount = filePostsCount;
    }

    @Override
    public String toString() {
        return String.format("fileName: %s | filePostsCount: %s ",
                this.fileName, this.filePostsCount);
    }

}
