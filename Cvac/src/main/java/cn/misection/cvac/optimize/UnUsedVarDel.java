package cn.misection.cvac.optimize;

import cn.misection.cvac.ast.IVisitor;
import cn.misection.cvac.ast.clas.CvaClass;
import cn.misection.cvac.ast.decl.CvaDeclaration;
import cn.misection.cvac.ast.entry.CvaEntry;
import cn.misection.cvac.ast.expr.*;
import cn.misection.cvac.ast.method.CvaMethod;
import cn.misection.cvac.ast.program.CvaProgram;
import cn.misection.cvac.ast.statement.*;
import cn.misection.cvac.ast.type.CvaBoolean;
import cn.misection.cvac.ast.type.CvaClassType;
import cn.misection.cvac.ast.type.CvaInt;

import java.util.Hashtable;

/**
 * Created by Mengxu on 2017/1/24.
 */
public class UnUsedVarDel implements IVisitor, Optimizable
{
    private Hashtable<String, CvaDeclaration> unUsedLocals;
    private Hashtable<String, CvaDeclaration> unUsedArgs;
    private boolean isOptimizing;
    public boolean givesWarning;

    @Override
    public void visit(CvaBoolean t)
    {
    }

    @Override
    public void visit(CvaClassType t)
    {
    }

    @Override
    public void visit(CvaInt t)
    {
    }

    @Override
    public void visit(CvaDeclaration d)
    {
    }

    @Override
    public void visit(CvaAddExpr e)
    {
        this.visit(e.getLeft());
        this.visit(e.getRight());
    }

    @Override
    public void visit(CvaAndAndExpr e)
    {
        this.visit(e.getLeft());
        this.visit(e.getRight());
    }

    @Override
    public void visit(CvaCallExpr e)
    {
        this.visit(e.getExpr());
        e.getArgs().forEach(this::visit);
    }

    @Override
    public void visit(CvaFalseExpr e)
    {
    }

    @Override
    public void visit(CvaIdentifier e)
    {
        if (this.unUsedLocals.containsKey(e.getLiteral()))
        {
            this.unUsedLocals.remove(e.getLiteral());
        }
        else if (this.unUsedArgs.containsKey(e.getLiteral()))
        {
            this.unUsedArgs.remove(e.getLiteral());
        }
    }

    @Override
    public void visit(CvaLTExpr e)
    {
        this.visit(e.getLeft());
        this.visit(e.getRight());
    }

    @Override
    public void visit(CvaNewExpr e)
    {
    }

    @Override
    public void visit(CvaNegateExpr e)
    {
        this.visit(e.getExpr());
    }

    @Override
    public void visit(CvaNumberInt e)
    {
    }

    @Override
    public void visit(CvaSubExpr e)
    {
        this.visit(e.getLeft());
        this.visit(e.getRight());
    }

    @Override
    public void visit(CvaThisExpr e)
    {
    }

    @Override
    public void visit(CvaMuliExpr e)
    {
        this.visit(e.getLeft());
        this.visit(e.getRight());
    }

    @Override
    public void visit(CvaTrueExpr e)
    {
    }

    @Override
    public void visit(CvaAssign s)
    {
        this.visit(new CvaIdentifier(s.getLineNum(), s.getLiteral()));
        this.visit(s.getExpr());
    }

    @Override
    public void visit(CvaBlock s)
    {
        s.getStatementList().forEach(this::visit);
    }

    @Override
    public void visit(CvaIfStatement s)
    {
        this.visit(s.getCondition());
        this.visit(s.getThenStatement());
        this.visit(s.getElseStatement());
    }

    @Override
    public void visit(CvaWriteOperation s)
    {
        this.visit(s.getExpr());
    }

    @Override
    public void visit(CvaWhileStatement s)
    {
        this.visit(s.getCondition());
        this.visit(s.getBody());
    }

    @Override
    public void visit(CvaMethod m)
    {
        this.unUsedLocals = new Hashtable<>();
        m.getLocalList().forEach(local ->
        {
            CvaDeclaration l = (CvaDeclaration) local;
            this.unUsedLocals.put(l.getLiteral(), l);
        });

        this.unUsedArgs = new Hashtable<>();
        m.getFormalList().forEach(formal ->
        {
            CvaDeclaration f = (CvaDeclaration) formal;
            this.unUsedArgs.put(f.getLiteral(), f);
        });

        m.getStatementList().forEach(this::visit);
        this.visit(m.getRetExpr());

        this.isOptimizing = this.unUsedArgs.size() > 0
                || this.unUsedLocals.size() > 0;
        this.unUsedArgs.forEach((uak, uao) ->
        {
            if (givesWarning)
            {
                System.out.printf("Warning: at line %d : the argument \"%s\" of" +
                                " method \"%s\" you have never used.%n",
                        uao.getLineNum(), uak, m.getLiteral());
            }
        });

        this.unUsedLocals.forEach((ulk, ulo) ->
        {
            if (givesWarning)
            {
                System.out.printf("Warning: at line %d : the local variable " +
                                "\"%s\" you have never used. Now we delete it.%n",
                        ulo.getLineNum(), ulk);
            }
            m.getLocalList().remove(ulo);
        });
    }

    @Override
    public void visit(CvaClass c)
    {
        c.getMethodList().forEach(this::visit);
    }

    @Override
    public void visit(CvaEntry c)
    {
    }

    @Override
    public void visit(CvaProgram p)
    {
        this.isOptimizing = false;
        p.getClassList().forEach(this::visit);
    }

    @Override
    public boolean isOptimizing()
    {
        return this.isOptimizing;
    }
}
