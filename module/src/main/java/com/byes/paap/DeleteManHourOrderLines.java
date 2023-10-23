package com.byes.paap;

import java.util.Iterator;

import com.planonsoftware.platform.backend.businessrule.v3.IBusinessRule;
import com.planonsoftware.platform.backend.businessrule.v3.IBusinessRuleContext;
import com.planonsoftware.platform.backend.data.v1.IAssociation;
import com.planonsoftware.platform.backend.data.v1.IBusinessObject;

public class DeleteManHourOrderLines implements IBusinessRule {

    @Override
    public void execute(IBusinessObject newBO, IBusinessObject oldBO, IBusinessRuleContext context) {

        IAssociation orderLines = newBO.getAssociationByName("OrderLine", "OrderRef");
        Iterator<IBusinessObject> orderLinesIterator = orderLines.getIterator();
        
        while (orderLinesIterator.hasNext()) {
            IBusinessObject orderLine = orderLinesIterator.next();

            IBusinessObject materialType = orderLine.getReferenceFieldByName("MaterialTypeRef").getValue();

            if (materialType != null) {
                String code = materialType.getStringFieldByName("Code").getValue();
                String description = orderLine.getStringFieldByName("Description").getValue();
                if ("0000".equals(code) && "Stunden".equals(description)) {
                    orderLine.delete();
                }
            }
        }


    }
}