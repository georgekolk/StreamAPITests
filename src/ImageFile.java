import java.nio.file.Path;

public class ImageFile {

    private String fileName;
    private Path path;
    private String tags;
    private String likes;

    public ImageFile() {
    }

    @Override
    public String toString() {
        return this.fileName;
    }

    public ImageFile(String fileName, Path path) {
        this.fileName = fileName;
        this.path = path;
    }

    public ImageFile(Path path) {
        this.path = path;
        this.fileName = path.getFileName().toString();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void showPath(){ System.out.println(this.path.toString());}
}
