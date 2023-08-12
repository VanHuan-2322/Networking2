import java.io.Serializable;

public class FileInfo implements Serializable {
    private String fileName;
    private int numParts;
    private int partSize;
    private long fileSize;

    public FileInfo(String fileName, int numParts, int partSize, long fileSize) {
        this.fileName = fileName;
        this.numParts = numParts;
        this.partSize = partSize;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public int getNumParts() {
        return numParts;
    }

    public int getPartSize() {
        return partSize;
    }

    public long getFileSize() {
        return fileSize;
    }
}
