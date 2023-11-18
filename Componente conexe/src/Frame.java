import java.io.File;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Frame extends JPanel{
    private static Vector<Node> nodes;
    private static Vector<Edge> edges;
    private int nodeNr = 1;
    private int radacina=-1;
    public void setRadacina(int radacina) {
        this.radacina = radacina;
    }
    private static int node_diam = 30;
    static int getNode_diam() {
        return node_diam;
    }
    public static Vector<Edge> getEdges() {
        return edges;
    }
    public static Vector<Node> getNodes() {
        return nodes;
    }
    private Point pointStart = null;
    private Point pointEnd = null;
    private boolean isDragging = false;

    private static Vector<Color> colors;
    public Frame() {
        nodes = new Vector<Node>();
        edges = new Vector<Edge>();
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    return;
                }
                pointStart = e.getPoint();
                isDragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)) {
                    return;
                }

                Vector<Vector<Integer>>adjencyMatrix=new Vector<Vector<Integer>>();
                pointEnd = e.getPoint();
                if(pointStart.equals(pointEnd)) {
                    Node newNode = new Node(pointStart, node_diam, nodeNr);
                    for (Node n : nodes) {
                        if (n.isInside(newNode.center, node_diam)) {
                            return;
                        }
                    }
                    nodes.add(newNode);
                    adjencyMatrix=AdjencyMatrix();
                    FilePrint filePrint=new FilePrint(adjencyMatrix);

                    determinaSiDeseneazaComponenteConexe();
                    nodeNr++;
                    isDragging = false;
                    repaint();
                    return;
                }
                isDragging = false;
                Node n1 = null;
                Node n2 = null;
                for (Node n : nodes) {
                    if (n.isInside(pointStart)) {
                        n1 = n;
                    }
                    if (n.isInside(pointEnd)) {
                        n2 = n;
                    }
                }
                if (n1 != null && n2 != null) {
                    Edge newEdge = new Edge(n1, n2);
                    boolean alreadyExists = false;
                    for (Edge e1 : edges) {
                            if (e1.getStart().equals(newEdge.getStart()) && e1.getEnd().equals(newEdge.getEnd()) || e1.getStart().equals(newEdge.getEnd()) && e1.getEnd().equals(newEdge.getStart())) {
                                alreadyExists = true;
                                System.out.println("Edge already exists");
                                break;
                            }
                    }
                    if(!alreadyExists) {
                       //set in matrix 1 at n1 and n2 with set
                        edges.add(newEdge);
                        adjencyMatrix=AdjencyMatrix();
                        FilePrint filePrint=new FilePrint(adjencyMatrix);

                        determinaSiDeseneazaComponenteConexe();
                    }
                }
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                repaint();
            }
        });
        //drag node with it edges attached when mouse is right clicked
        this.addMouseMotionListener(new MouseMotionAdapter() {
            Node draggedNode = null;
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (draggedNode == null) {
                        for (Node n : nodes) {
                            if (n.isInside(e.getPoint())) {
                                draggedNode = n;
                                break;
                            }
                        }
                    } else {
                        for(Node n:nodes){
                            if(n!=draggedNode && n.isInside(e.getPoint(),n.diameter)){
                                return;
                            }
                        }
                        draggedNode.setCenter(e.getPoint());
                        repaint();
                    }
                }
            }


            @Override
            public void mouseMoved(MouseEvent e) {
                draggedNode = null;
            }
        });

        //initializare culori
        colors=new Vector<Color>();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Edge e : edges) {
            e.drawEdge(g);
        }
        for (Node n : nodes) {
                n.drawNode(g);
        }
        if (isDragging) {
            g.setColor(Color.RED);
            g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
        }
    }
    static Vector<Vector<Integer>>AdjencyMatrix(){
        Vector<Vector<Integer>>adjencyMatrix=new Vector<Vector<Integer>>();
        for(int i=0;i<nodes.size();i++){
            Vector<Integer>line=new Vector<Integer>();
            for(int j=0;j<nodes.size();j++){
                line.add(0);
            }
            adjencyMatrix.add(line);
        }
        for(Edge e:edges){
            adjencyMatrix.get(e.getStart().getID()-1).set(e.getEnd().getID()-1,1);
            adjencyMatrix.get(e.getEnd().getID()-1).set(e.getStart().getID()-1,1);
        }
        return adjencyMatrix;
    }

    void undo(){
        if(edges.size()>0){
            edges.remove(edges.size()-1);
            Vector<Vector<Integer>>adjencyMatrix=new Vector<Vector<Integer>>();
            adjencyMatrix=AdjencyMatrix();
            FilePrint filePrint=new FilePrint(adjencyMatrix);
            determinaSiDeseneazaComponenteConexe();
        }
    }

    void determinaSiDeseneazaComponenteConexe(){
        ComponenteConexe2 componenteConexe = new ComponenteConexe2();
        componenteConexe.createListaAdiacenta();
        ArrayList<ArrayList<Integer>> componenteConexeList = componenteConexe.determinaComponenteConexe();

        // Generăm culori diferite pentru fiecare componentă conexă
        ArrayList<Color> culori = new ArrayList<>();
        for (int i = 0; i < componenteConexeList.size(); i++) {
            culori.add(generateRandomColor());
        }

        //print componente conexe
        for (int i = 0; i < componenteConexeList.size(); i++) {
            System.out.print("Componenta conexa " + (i + 1) + ": ");
            for (int j = 0; j < componenteConexeList.get(i).size(); j++) {
                System.out.print((componenteConexeList.get(i).get(j)+1) + " ");
            }
            System.out.println();
        }

        // Atribuim culorile direct nodurilor
        for (int i = 0; i < componenteConexeList.size(); i++) {
            for (int j = 0; j < componenteConexeList.get(i).size(); j++) {
                nodes.get(componenteConexeList.get(i).get(j)-1).setColor(culori.get(i));
            }
        }

        repaint(); // Actualizăm afișarea
    }
    // Funcție pentru a genera o culoare aleatoare
    private Color generateRandomColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

}
