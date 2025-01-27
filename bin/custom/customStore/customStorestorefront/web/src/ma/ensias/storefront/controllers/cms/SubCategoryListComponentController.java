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

import de.hybris.platform.acceleratorcms.model.components.SubCategoryListComponentModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import ma.ensias.storefront.controllers.ControllerConstants;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for CMS SubCategoryListComponent
 */
@Controller("SubCategoryListComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.SubCategoryListComponent)
public class SubCategoryListComponentController extends AbstractAcceleratorCMSComponentController<SubCategoryListComponentModel>
{
	@Resource(name = "commerceCategoryService")
	private CommerceCategoryService commerceCategoryService;

	@Resource(name = "categoryConverter")
	private Converter<CategoryModel, CategoryData> categoryConverter;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final SubCategoryListComponentModel component)
	{
		final SearchPageData searchPageData = getRequestContextData(request).getSearch();
		if (searchPageData instanceof ProductCategorySearchPageData)
		{
			final ProductCategorySearchPageData<?, ?, CategoryData> productCategorySearchPageData = (ProductCategorySearchPageData<?, ?, CategoryData>) searchPageData;
			model.addAttribute("subCategories", productCategorySearchPageData.getSubCategories());
		}
		else
		{
			final CategoryModel categoryModel = getRequestContextData(request).getCategory();
			if (categoryModel != null)
			{
				model.addAttribute("subCategories", Converters.convertAll(categoryModel.getAllSubcategories(), categoryConverter));
			}
		}
	}
}
