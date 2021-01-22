package com.itafin.lifeline.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itafin.lifeline.model.Applicant;

@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionAdvice {
	
	private static final Logger logger = Logger.getLogger(GlobalExceptionAdvice.class); 

	@ExceptionHandler(MaxUploadSizeExceededException.class)
  public String handleMaxSizeException(
      MaxUploadSizeExceededException e,
      RedirectAttributes redirectAttributes,
      HttpServletRequest request,
      HttpServletResponse response) {
    
    ModelAndView modelAndView = new ModelAndView("index");
    modelAndView.getModel().put("msg", "File too large!");
    return "redirect:/main";
	}
	
	@ExceptionHandler(FileUploadException.class)
	public String handleMaxRequestException(
			FileUploadException e,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request, 
			HttpServletResponse response
			) {
		
		logger.error("FileUploadException caught. Max request size exceeded. File upload size was too large.", e);
		
		return "applicantFormView";
	}
  
}
