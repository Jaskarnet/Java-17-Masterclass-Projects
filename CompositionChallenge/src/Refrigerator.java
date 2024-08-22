public class Refrigerator {
    boolean hasWorkToDo;

    public Refrigerator() {
        this.hasWorkToDo = false;
    }

    public boolean isHasWorkToDo() {
        return hasWorkToDo;
    }

    public void setHasWorkToDo(boolean hasWorkToDo) {
        this.hasWorkToDo = hasWorkToDo;
    }

    public void orderFood() {
        if (hasWorkToDo) {
            System.out.println("Refrigerator is ordering food.");
            hasWorkToDo = false;
        }
    }
}
