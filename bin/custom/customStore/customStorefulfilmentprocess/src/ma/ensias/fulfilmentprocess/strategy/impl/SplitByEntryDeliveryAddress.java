/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package ma.ensias.fulfilmentprocess.strategy.impl;

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.strategy.AbstractSplittingStrategy;


/**
 * Splitting by Delivery Address with support for Pickup In Store
 */
public class SplitByEntryDeliveryAddress extends AbstractSplittingStrategy
{

	@Override
	public Object getGroupingObject(final AbstractOrderEntryModel orderEntry)
	{
		AddressModel shippingAddress = null;

		if (orderEntry.getDeliveryAddress() != null)
		{
			shippingAddress = orderEntry.getDeliveryAddress();
		}
		else if (orderEntry.getDeliveryPointOfService() != null && orderEntry.getDeliveryPointOfService().getAddress() != null)
		{
			shippingAddress = orderEntry.getDeliveryPointOfService().getAddress();
		}
		else
		{
			shippingAddress = orderEntry.getOrder().getDeliveryAddress();
		}
		return shippingAddress;
	}

	@Override
	public void afterSplitting(final Object groupingObject, final ConsignmentModel createdOne)
	{
		createdOne.setShippingAddress((AddressModel) groupingObject);
	}

}
