package cn.misection.cvac.codegen.bst.bstatement;

import cn.misection.cvac.codegen.bst.Label;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName LabelJ
 * @Description TODO
 * @CreateTime 2021年02月16日 00:58:00
 */
public class LabelJ extends BaseStatement
{
    private Label label;

    public LabelJ(Label label)
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
