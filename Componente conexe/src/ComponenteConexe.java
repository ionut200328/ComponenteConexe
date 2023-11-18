import java.util.*;
public class ComponenteConexe {
    private static ArrayList<ArrayList<Integer>>ListaAdiacenta=new ArrayList<ArrayList<Integer>>();

    ArrayList<ArrayList<Integer>>getListaAdiacenta()
    {
        return ListaAdiacenta;
    }
    private static Vector<Vector<Integer>>AdjencyMatrix;

    void createListaAdiacenta()
    {
        AdjencyMatrix=Frame.AdjencyMatrix();
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
        boolean[] visited = new boolean[ListaAdiacenta.size()];
        ArrayList<ArrayList<Integer>> componenteConexe = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < ListaAdiacenta.size(); i++) {
            if (!visited[i]) {
                componenteConexe.add(new ArrayList<Integer>());
                Stack<Integer> stack = new Stack<>();
                stack.push(i);
                visited[i] = true;

                while (!stack.isEmpty()) {
                    int node = stack.pop();
                    componenteConexe.get(componenteConexe.size() - 1).add(node);
                    for (int neighbor : ListaAdiacenta.get(node)) {
                        if (!visited[neighbor]) {
                            stack.push(neighbor);
                            visited[neighbor] = true;
                        }
                    }
                }
            }
        }
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

    public int determinaComponentaConexaNod(int i) {
        boolean[] visited = new boolean[ListaAdiacenta.size()];
        int componenta = 0;

        if (!visited[i]) {
            componenta++;
            Stack<Integer> stack = new Stack<>();
            stack.push(i);
            visited[i] = true;

            while (!stack.isEmpty()) {
                int node = stack.pop();
                for (int neighbor : ListaAdiacenta.get(node)) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                        visited[neighbor] = true;
                    }
                }
            }
        }
        return componenta;
    }
}
