package com.itafin.lifeline.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itafin.lifeline.dao.LifelineDaoImpl;
import com.itafin.lifeline.model.Address;
import com.itafin.lifeline.model.Applicant;
import com.itafin.lifeline.model.Supplier;
import com.itafin.lifeline.model.UserAccount;
import com.itafin.lifeline.model.Utility;
import com.itafin.lifeline.service.CaptchaService;
import com.itafin.lifeline.service.LifelineService;
import com.itafin.lifeline.service.ScersService;
import com.itafin.lifeline.utils.Encrypter;
import com.itafin.lifeline.utils.LifelineProperties;
import com.itafin.lifeline.utils.LifelineUtils;
import com.itafin.lifeline.validation.ApplicantFormValidator;

@Controller
@RequestMapping("/")
@SessionAttributes("applicant")
public class LifelineController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(LifelineController.class);
	
	private static final String APPLICATION_PAGE = "applicantFormView";
	private static final String CONFIRMATION_PAGE = "applicantConfirmView";
	private static final String END_PAGE = "applicantEndView";
	private static final String ERROR_PAGE = "defaultErrorPage";
	private static final String FAQ_PAGE = "faq";
	
	@Autowired LifelineProperties lifelineProps;
	@Autowired LifelineService lifelineService;
	@Autowired ScersService scersService;
	@Autowired CaptchaService captchaService;
	@Autowired ApplicantFormValidator applicantFormValidator;
	
	@ModelAttribute("applicant")
	public Applicant newApplicant() {
		return new Applicant();
	}

	
	// RequestMapping value = URL i.e. localhost:8080/Lifeline/main
	@RequestMapping(value = { "/main", "/" }, method = {RequestMethod.GET})
	public String viewMain(Model model) {
		// viewName = <viewName>.jsp i.e. mainSelectView.jsp in WebContent/WEB-INF/views/
		//ModelAndView viewToReturn = new ModelAndView("applicantFormView");
		submitInvalidated();
		populateSupplierLists(model);
		populateStaticLists(model);
		//model.addAttribute("applicant", new Applicant());
		return APPLICATION_PAGE;
	}
	
	@RequestMapping(value = { "/faq" }, method = {RequestMethod.GET})
	public String viewFAQ(Model model) {
		return FAQ_PAGE;
	}
	
	// submit for server side validation
	//@RequestMapping(value = { "/validateApplication" }, method = {RequestMethod.GET})
	private String validateApplication(
		Applicant applicant, boolean mailingSameAsService, boolean validateDocuments,
		BindingResult result, Model model) {
		
		if(mailingSameAsService) {
			applicant.setMailingAddress(applicant.getServiceAddress());
		}
		model.addAttribute("mailingSameAsService", mailingSameAsService);
		
		applicantFormValidator.setValidateDocuments(validateDocuments);
		applicantFormValidator.validate(applicant, result);
		
		// kludge until we can get supplierConverter working
		Utility blankUtil = new Utility();
		if(null != applicant.getUtility() && applicant.getUtility().size() >= 3) {
			if(!applicant.getUtility().get(2).equals(blankUtil)) {
				applicant.getUtility().get(2).setSupplier(scersService.getSupplierByCode(applicant.getUtility().get(2).getSupplierCode()));
			}
		}
		
		if(null != applicant.getUtility() && applicant.getUtility().size() >= 4) {
			if(!applicant.getUtility().get(3).equals(blankUtil)) {
				applicant.getUtility().get(3).setSupplier(scersService.getSupplierByCode(applicant.getUtility().get(3).getSupplierCode()));
			}
		}
		
		// insert flags to re-expand utility sections if they were previously filled out
		if(null != applicant.getUtility()) {
			for(int i = 0; i < applicant.getUtility().size(); i++) {
				boolean utilityNotEmpty = lifelineService.utilityIsNotEmpty(applicant.getUtility().get(i));
				if(utilityNotEmpty) {
					model.addAttribute("utilityAdded"+i, utilityNotEmpty);
				}
			}
		}
		
		if(result.hasErrors()) {
			populateSupplierLists(model);
			populateStaticLists(model);
			result.reject("Global.applicantForm.requirements");
			Set<ObjectError> globalErrors = new HashSet<>();
			for(ObjectError objectError : result.getGlobalErrors()) {
				globalErrors.add(objectError);
			}
			model.addAttribute("globalErrors", globalErrors);
			model.addAttribute("applicant", applicant);
			if(validateDocuments) {	// came from confirm
				return CONFIRMATION_PAGE;
			} else {	// came from submit
				return APPLICATION_PAGE;
			}
		} else {
			if(validateDocuments) {
				return "redirect:/end";
			} else {
				return CONFIRMATION_PAGE;
			}
		}
	}
	
	private void validateDocuments(MultipartFile[] documents, Model model) {
		for(int i = 0; i < documents.length; i++) {
			if(documents[i].getSize() > lifelineProps.getMaxUploadFileSize()) { // 1048576 bytes = 1MB
				String errorMsg_Id = "docMsg" + String.valueOf(i);
				model.addAttribute(errorMsg_Id , "File size exceeded 1MB");
			}
		}
	}
	
	@RequestMapping(value = { "/submit" }, method = {RequestMethod.GET}) 
	public String submitInvalidated() {
		return "redirect:/main";
	}

	//	form validation step
	@RequestMapping(value = { "/submit" }, method = {RequestMethod.POST})
	public String submit(
		@ModelAttribute("applicant") @Validated Applicant applicant,
		@RequestParam(value="sameAsServiceChecked", defaultValue="false") boolean mailingSameAsService,
		BindingResult result, Model model) {
		
		Applicant emptyApp = new Applicant();
		if(applicant.equals(emptyApp)) {
			return "redirect:/main";
		}
		
		boolean validateDocuments = false;
		String viewToReturn = validateApplication(applicant, mailingSameAsService, validateDocuments, result, model);
		model.addAttribute("siteKey", lifelineProps.getProperties().getProperty("recaptcha.sitekey"));

		return viewToReturn;
	}
	
	//	submit to database for review
	//	submit documents for validation on this step because I 
	// couldn't find a way to keep the MultipartFiles "live" on the confirmation page after submitting initially via /submit
	@RequestMapping(value = { "/confirm" }, method = {RequestMethod.POST})
	public String confirm(
		@ModelAttribute("applicant") @Validated Applicant applicant,
		@RequestParam(value="sameAsServiceChecked", defaultValue="false") boolean mailingSameAsService,
		BindingResult result, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, SessionStatus session) {
		
		model.addAttribute("siteKey", lifelineProps.getProperties().getProperty("recaptcha.sitekey"));
		try {
			captchaService.processResponse( request.getParameter("g-recaptcha-response"), request.getRemoteAddr());
		} catch (Exception captchaEx) {
			captchaEx.printStackTrace();
			logger.error(captchaEx.getMessage());
			result.reject("fieldErrors", "Captcha failed validation.  Please try again.");
			redirectAttributes.addFlashAttribute("errors", result);
			return "redirect:/submit";
		}
		
		boolean validateDocuments = true;
		String viewToReturn = validateApplication(applicant, mailingSameAsService, validateDocuments, result, model);
		if("redirect:/end".equals(viewToReturn)) {
			String applicationNumber = lifelineService.insertApplicant(applicant);
			if(null == applicationNumber || applicationNumber.isEmpty()) {
				viewToReturn = ERROR_PAGE;
			} else {
				session.setComplete();
				applicant.setAccountNum(applicationNumber);
				redirectAttributes.addFlashAttribute("emailAddress", applicant.getEmailAddress());
				redirectAttributes.addFlashAttribute("applicationNumber", applicant.getAccountNum());
			}
		}
		
		return viewToReturn;
	}
	
	@RequestMapping(value = {"/end"}, method = {RequestMethod.GET})
	public String end(Model model, SessionStatus session) {
		String applicationNumber = (String) model.asMap().get("applicationNumber");
		if(null == applicationNumber || applicationNumber.isEmpty()) {
			return "redirect:/main";
		}
		String viewToReturn = "applicantEndView";
		session.setComplete();
		return viewToReturn;
	}
	
	public void populateSupplierLists(Model model) {
		List<Supplier> suppliers = scersService.getActiveSupplierList();
		List<Supplier> landlineSuppliers = new ArrayList<Supplier>();
		List<Supplier> phoneSuppliers = new ArrayList<Supplier>();
		for(int i = 0; i < suppliers.size(); i++) {
			if("P".equals(suppliers.get(i).getUtilityType())) {
				phoneSuppliers.add(suppliers.get(i));
			} else if("L".equals(suppliers.get(i).getUtilityType())) {
				landlineSuppliers.add(suppliers.get(i));
			}
		}
		
		model.addAttribute("landlineSuppliers", landlineSuppliers);
		model.addAttribute("phoneSuppliers", phoneSuppliers);
	}
	
	// fills out lists for things like directional, street type, states, etc
	public void populateStaticLists(Model model) {
		// directions
		String[] arr_directions = {"N", "S", "E", "W", "NE", "NW", "SE", "SW"};
		List<String> l_directions = Arrays.asList(arr_directions);
		model.addAttribute("streetDirections", l_directions);
	}
	
}
