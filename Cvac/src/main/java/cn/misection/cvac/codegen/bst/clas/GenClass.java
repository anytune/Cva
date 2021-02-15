package cn.misection.cvac.codegen.bst.clas;

import cn.misection.cvac.codegen.bst.decl.AbstractDeclaration;
import cn.misection.cvac.codegen.bst.method.AbstractMethod;

import java.util.List;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName CvaClass
 * @Description TODO
 * @CreateTime 2021年02月14日 19:57:00
 */
public class GenClass extends BaseClass
{
    private String literal;

    private String parent;

    private List<AbstractDeclaration> fieldList;

    private List<AbstractMethod> methodList;

    public GenClass(String literal, String parent, List<AbstractDeclaration> fieldList, List<AbstractMethod> methodList)
    {
        this.literal = literal;
        this.parent = parent;
        this.fieldList = fieldList;
        this.methodList = methodList;
    }

    public String getLiteral()
    {
        return literal;
    }

    public String getParent()
    {
        return parent;
    }

    public List<AbstractDeclaration> getFieldList()
    {
        return fieldList;
    }

    public List<AbstractMethod> getMethodList()
    {
        return methodList;
    }
}