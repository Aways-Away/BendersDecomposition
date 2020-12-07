import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloLinearNumExprIterator;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

/**
 * Created by AwaysAway on 2020/12/4
 */
public class Benders {
    /**
     * Benders Decomposition
     */
    public static void Model() throws IloException {
        IloCplex MP = new IloCplex();
        IloNumVar[] x = MP.numVarArray(2, 0, Double.MAX_VALUE);
        IloLinearNumExpr obj = MP.linearNumExpr();
        obj.addTerm(1, x[0]);
        obj.addTerm(1, x[1]);
        MP.addMaximize(obj);
        IloLinearNumExpr c = MP.linearNumExpr();
        c.addTerm(2, x[0]);
        c.addTerm(1, x[1]);
        MP.addLe(100, c);
        MP.setParam(IloCplex.BooleanParam.PreInd, false);
        MP.setParam(IloCplex.IntParam.RootAlg,IloCplex.Algorithm.Primal);
        MP.solve();
        IloLinearNumExpr ray = MP.getRay();
        System.out.println("getRay returned " + ray.toString());
        IloLinearNumExprIterator it = ray.linearIterator();
        double xcoef = 0, ycoef = 0;
        while (it.hasNext()) {
            IloNumVar v = it.nextNumVar();
            if (v.equals(x[0])) {
                xcoef = it.getValue();
            }
            else if (v.equals(x[1])) {
                ycoef = it.getValue();
            }
        }
        System.out.println("Extreme ray direction = (" + xcoef + ", " + ycoef + ")");
        System.out.println(MP.getStatus());
        System.out.println(x[0]);
        System.out.println(x[1]);

//        try{
//            IloCplex cplex = new IloCplex();
//            /**
//             * 先把先行求解关了 然后设置求解方法为基本单纯形法
//             */
//            cplex.setParam(IloCplex.BooleanParam.PreInd, false);
//            cplex.setParam(IloCplex.IntParam.RootAlg,IloCplex.Algorithm.Primal);
//
//            /**
//             * variables
//             */
//            IloNumVar x11 = cplex.numVar(-100,Integer.MAX_VALUE,"alpha11");
//            IloNumVar x12 = cplex.numVar(-100,Integer.MAX_VALUE,"alpha12");
//            IloNumVar x21 = cplex.numVar(-100,Integer.MAX_VALUE,"alpha21");
//
//            IloNumVar y1 = cplex.numVar(-200,0,"beta1");
//            IloNumVar y2 = cplex.numVar(-200,0,"beta2");
//            IloNumVar y3 = cplex.numVar(-200,0,"beta3");
//            IloNumVar y4 = cplex.numVar(-200,0,"beta4");
//            IloNumVar y5 = cplex.numVar(-200,0,"beta5");
//
//            /**
//             * 极线
//             */
//
//            /**
//             * objective
//             */
//            IloLinearNumExpr objective = cplex.linearNumExpr();
//            objective.addTerm(8,x11);
//            objective.addTerm(3,x12);
//            objective.addTerm(5,x21);
//
//            cplex.addMaximize(objective);
//
//            /**
//             * constrains
//             */
//            cplex.addLe(cplex.sum(cplex.prod(1,x11), cplex.prod(1,y1)),1);
//            cplex.addLe(cplex.sum(cplex.prod(1,x12), cplex.prod(1,y2)), 1);
//            cplex.addLe(cplex.sum(cplex.prod(1,x21), cplex.prod(1,y3)), 1);
//            cplex.addLe(cplex.sum(cplex.prod(1,x11), cplex.prod(1,x21), cplex.prod(1,y4)), 1);
//            cplex.addLe(cplex.sum(cplex.prod(1,x11), cplex.prod(1,x12), cplex.prod(1,y5)), 1);
//
//
//
//            /**
//             * solve
//             */
//            cplex.solve();
//            IloLinearNumExpr ray = cplex.getRay();
//            System.out.println("getRay returned " + ray.toString());
//            IloLinearNumExprIterator it = ray.linearIterator();
//        } catch (IloException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) throws IloException {
        Model();


    }
}
