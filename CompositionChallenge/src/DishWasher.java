public class DishWasher {
    boolean hasWorkToDo;

    public DishWasher() {
        this.hasWorkToDo = false;
    }

    public boolean isHasWorkToDo() {
        return hasWorkToDo;
    }

    public void setHasWorkToDo(boolean hasWorkToDo) {
        this.hasWorkToDo = hasWorkToDo;
    }

    public void doDishes() {
        if (hasWorkToDo) {
            System.out.println("Dishwasher is doing dishes.");
            hasWorkToDo = false;
        }
    }
}
