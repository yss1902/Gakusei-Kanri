package net.datasa.Gakusei_Kanri.service;

import net.datasa.Gakusei_Kanri.domain.dto.StudentDTO;
import net.datasa.Gakusei_Kanri.domain.dto.StudentForm;

import java.util.List;

public interface StudentService {
    StudentDTO create(StudentForm form);

    StudentDTO findOne(Long id);

    List<StudentDTO> findAll();

    StudentDTO update(Long id, StudentForm form);

    void delete(Long id);

    StudentForm getForm(Long id);
}
