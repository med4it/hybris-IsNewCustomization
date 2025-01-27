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
package ma.ensias.storefront.controllers.cms;

import de.hybris.platform.acceleratorcms.model.components.CartSuggestionComponentModel;
import de.hybris.platform.acceleratorcms.model.components.SimpleSuggestionComponentModel;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.cms.AbstractCMSComponentController;
import de.hybris.platform.commercefacades.order.CartFacade;
import ma.ensias.facades.suggestion.SimpleSuggestionFacade;
import ma.ensias.storefront.controllers.ControllerConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for CMS CartSuggestionComponent
 */
@Controller("CartSuggestionComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.CartSuggestionComponent)
public class CartSuggestionComponentController extends AbstractCMSComponentController<CartSuggestionComponentModel>
{
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "simpleSuggestionFacade")
	private SimpleSuggestionFacade simpleSuggestionFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final CartSuggestionComponentModel component)
	{
		if (cartFacade.hasSessionCart())
		{
			model.addAttribute("title", component.getTitle());
			model.addAttribute(
					"suggestions",
					simpleSuggestionFacade.getSuggestionsForProductsInCart(component.getProductReferenceTypes(),
							component.isFilterPurchased(), component.getMaximumNumberProducts()));
		}
	}

	@Override
	protected String getView(final CartSuggestionComponentModel component)
	{
		return ControllerConstants.Views.Cms.ComponentPrefix + StringUtils.lowerCase(SimpleSuggestionComponentModel._TYPECODE);
	}
}
