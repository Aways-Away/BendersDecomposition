import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloLinearNumExprIterator;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

/**
 * Created by AwaysAway on 2020/12/5
 */
public class BenComposition {
    public static void model() throws Exception{
        IloCplex cplex = new IloCplex();

        /**
         * variables
         */
        IloNumVar x11 = cplex.numVar(-100,Integer.MAX_VALUE,"alpha11");
        IloNumVar x12 = cplex.numVar(-100,Integer.MAX_VALUE,"alpha12");
        IloNumVar x21 = cplex.numVar(-100,Integer.MAX_VALUE,"alpha21");

        IloNumVar y1 = cplex.numVar(-200,0,"beta1");
        IloNumVar y2 = cplex.numVar(-200,0,"beta2");
        IloNumVar y3 = cplex.numVar(-200,0,"beta3");
        IloNumVar y4 = cplex.numVar(-200,0,"beta4");
        IloNumVar y5 = cplex.numVar(-200,0,"beta5");



        /**
         * objective
         */
        IloLinearNumExpr objective = cplex.linearNumExpr();
        objective.addTerm(8,x11);
        objective.addTerm(3,x12);
        objective.addTerm(5,x21);

        cplex.addMaximize(objective);

        /**
         * constrains
         */
        cplex.addLe(cplex.sum(cplex.prod(1,x11), cplex.prod(1,y1)),1);
        cplex.addLe(cplex.sum(cplex.prod(1,x12), cplex.prod(1,y2)), 1);
        cplex.addLe(cplex.sum(cplex.prod(1,x21), cplex.prod(1,y3)), 1);
        cplex.addLe(cplex.sum(cplex.prod(1,x11), cplex.prod(1,x21), cplex.prod(1,y4)), 1);
        cplex.addLe(cplex.sum(cplex.prod(1,x11), cplex.prod(1,x12), cplex.prod(1,y5)), 1);

        /**
         * 设置求解方法为基本单纯形法
         */
        cplex.setParam(IloCplex.BooleanParam.PreInd, false);
        cplex.setParam(IloCplex.IntParam.RootAlg,IloCplex.Algorithm.Primal);
        cplex.solve();

        /**
         * 极线
         */
        IloLinearNumExpr ray = cplex.getRay();
        System.out.println("getRay returned " + ray.toString());
        IloLinearNumExprIterator it = ray.linearIterator();

    }
    public static void main(String[] args) throws Exception {
        model();

    }
}
