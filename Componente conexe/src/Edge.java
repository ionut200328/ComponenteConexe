import java.awt.*;

public class Edge {
    private Node start;
    public Node getStart() {
        return start;
    }
    private Node end;
    public Node getEnd() {
        return end;
    }
    public void drawEdge(Graphics g) {
        g.setColor(Color.BLACK);
        if(this.start.center.x==this.end.center.x && this.start.center.y==this.end.center.y)
            g.drawOval(start.center.x - Frame.getNode_diam(), start.center.y - Frame.getNode_diam()+3, Frame.getNode_diam()*2, Frame.getNode_diam());
        else
            //g.drawLine(start.center.x, start.center.y, end.center.x, end.center.y
        g.drawLine(start.center.x, start.center.y, end.center.x, end.center.y);
    }
    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(Node node) {
        start = node;
    }

    public void setEnd(Node node) {
        end = node;
    }
}
