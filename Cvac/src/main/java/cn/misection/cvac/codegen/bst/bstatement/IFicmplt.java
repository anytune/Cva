package cn.misection.cvac.codegen.bst.bstatement;

import cn.misection.cvac.codegen.bst.Label;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName IFicmplt
 * @Description TODO
 * @CreateTime 2021年02月16日 00:51:00
 */
public class IFicmplt extends BaseStatement
{
    private Label label;

    public IFicmplt(Label label)
    {
        this.label = label;
    }

    public Label getLabel()
    {
        return label;
    }

    public void setLabel(Label label)
    {
        this.label = label;
    }
}
