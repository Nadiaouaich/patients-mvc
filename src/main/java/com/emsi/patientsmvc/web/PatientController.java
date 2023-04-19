package com.emsi.patientsmvc.web;

import com.emsi.patientsmvc.entities.Patient;
import com.emsi.patientsmvc.repositories.patientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private patientRepository patientRepository;

    @GetMapping(path="/user/index")
    public String patients(Model model ,
                          @RequestParam(name="page", defaultValue="0") int page ,
                          @RequestParam(name="size", defaultValue="5")int size ,
                          @RequestParam(name="keyword", defaultValue = "") String keyword

    ){
        Page<Patient> pagePatients=patientRepository.findByNomContains(keyword , PageRequest.of(page,size));
        model.addAttribute( "ListPatients", pagePatients.getContent()) ;
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute( "keyword", keyword);

        return "patients";
    }
     @GetMapping("/admin/delete")
      public String delete(Long id , String keyword , int page) {
         patientRepository.deleteById(id);
         return "redirect:/user/index?page="+page+"&keyword="+keyword;
     }

    @GetMapping("/patients")
    @ResponseBody
            public List<Patient> lisPatients(){

            return patientRepository.findAll();
            }
@GetMapping("/admin/formPatients")
  public String formPatients(Model model){
       model.addAttribute("patient",new Patient());
        return "formPatients";
  }


  @PostMapping(path = "/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult  bindingResult){
            if(bindingResult.hasErrors()) return "formPatients";
            patientRepository.save(patient);
            return "redirect:/formPatients";
    }
    @PostMapping(path = "/editSave")
    public String editSave(Model model, @Valid Patient patient, BindingResult  bindingResult,int page,String keyword){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/editPatients")
    public String editPatients(Model model,Long id,int page,String keyword){
        Patient patient=patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("currentPage",page);
        model.addAttribute( "keyword", keyword);
        return "editPatients";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }

}