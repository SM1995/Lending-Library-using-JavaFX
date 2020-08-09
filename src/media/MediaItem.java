package media;

public class MediaItem {

    private String title;
    private String format;
    private boolean onLoan;
    private String loanedTo;
    private String dateLoaned;

    public MediaItem() {
        title = null;
        format = null;
        onLoan = false;
        loanedTo = null;
        dateLoaned = null;
    }

    public MediaItem(String title, String format) {
        this.title = title;
        this.format = format;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    public String getLoanedTo() {
        return loanedTo;
    }

    public void setLoanedTo(String loanedTo) {
        this.loanedTo = loanedTo;
    }

    public String getDateLoaned() {
        return dateLoaned;
    }

    public void setDateLoaned(String dateLoaned) {
        this.dateLoaned = dateLoaned;
    }

    public boolean markOnLoan(String name, String date) {
        if (onLoan) {
            System.out.println(title + " loaned to " + name + " on " + date);
            return false;
        } else {
            setLoanedTo(name);
            setDateLoaned(date);
            setOnLoan(true);
            return true;
        }
    }

    public boolean markReturned() {
        if (!isOnLoan()) {
            //System.out.println(title +" is currently not on loan");
            return false;
        } else {
            setOnLoan(false);
            setLoanedTo(null);
            setDateLoaned(null);
            return true;
        }
    }
}
