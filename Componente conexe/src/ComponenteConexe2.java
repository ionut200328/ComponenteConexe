import java.util.*;

public class ComponenteConexe2 {
    private int s;         // Declare s
    private static ArrayList<ArrayList<Integer>>ListaAdiacenta=new ArrayList<ArrayList<Integer>>();

    ArrayList<ArrayList<Integer>>getListaAdiacenta()
    {
        return ListaAdiacenta;
    }
    private static Vector<Vector<Integer>>AdjencyMatrix;

    void createListaAdiacenta()
    {
        FilePrint filePrint=new FilePrint();
        AdjencyMatrix=filePrint.FileRead();

        if(AdjencyMatrix.size()==0)
        {
            return;
        }
        ListaAdiacenta.clear();
        for(int i=0;i<AdjencyMatrix.size();i++)
        {
            ArrayList<Integer>list=new ArrayList<Integer>();
            for(int j=0;j<AdjencyMatrix.size();j++)
            {
                if(AdjencyMatrix.get(i).get(j)==1)
                {
                    list.add(j);
                }
            }
            ListaAdiacenta.add(list);
        }
    }

    // Metoda pentru a determina toate componentele conexe
    ArrayList<ArrayList<Integer>> determinaComponenteConexe() {

        s=1;

        ArrayList<ArrayList<Integer>> componenteConexe = new ArrayList<ArrayList<Integer>>();

        Vector<Integer> U = new Vector<Integer>();
        Stack<Integer> V = new Stack<Integer>();
        Vector<Integer> W = new Vector<Integer>();
        int p = 1;
        ArrayList<Integer> N = new ArrayList<Integer>();
        ArrayList<Queue<Integer>> A = new ArrayList<Queue<Integer>>();
        for (int i = 0; i < ListaAdiacenta.size(); i++) {
            Queue<Integer> queue = new LinkedList<Integer>();
            for(int a:ListaAdiacenta.get(i))
            {
                queue.add(a);
            }
            A.add(queue);
        }

        for(int i=0;i<ListaAdiacenta.size();i++)
        {
            if(i+1==s)
            {
                continue;
            }
            U.add(i+1);
        }

        V.push(s);
        N.add(s);

        while (W.size() != AdjencyMatrix.size())
        {
            while (!V.isEmpty())
            {
                int x=V.peek();
                int y;
                if(A.get(x-1).isEmpty())
                {
                    V.pop();
                    W.add(x);
                    continue;
                }
                y=A.get(x-1).peek()+1;
                if(U.contains(y)) {
                    A.get(x-1).remove();
                    A.get(y-1).remove(x-1);
                    U.remove(U.indexOf(y));
                    V.push(y);
                    N.add(y);
                }
                else {
                    V.pop();
                    W.add(x);
                }
            }
            System.out.println("p: "+p);
            System.out.print("N: ");
            for(int i=0;i<N.size();i++)
            {
                System.out.print(N.get(i)+" ");
            }
            componenteConexe.add(new ArrayList<>(N));
            System.out.println();
            if(!U.isEmpty())
            {
                s=U.get(0);
                U.remove(0);
                p++;
                V.push(s);
                N.clear();
                N.add(s);
            }
        }
        componenteConexe.add(new ArrayList<>(N));
        return componenteConexe;
    }

    void printListaAdiacenta()
    {
        for(int i=0;i<ListaAdiacenta.size();i++)
        {
            System.out.print((i+1)+": ");
            for(int j=0;j<ListaAdiacenta.get(i).size();j++)
            {
                System.out.print((ListaAdiacenta.get(i).get(j)+1)+" ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        ComponenteConexe2 componenteConexe = new ComponenteConexe2();
        componenteConexe.createListaAdiacenta();
        componenteConexe.printListaAdiacenta();
        ArrayList<ArrayList<Integer>> componenteConexeList = componenteConexe.determinaComponenteConexe();
        System.out.println("Componente conexe:");
        for (ArrayList<Integer> componenta : componenteConexeList) {
            for (int nod : componenta) {
                System.out.print((nod) + " ");
            }
            System.out.println();
        }
    }
}
