package thelaborseekers.jobhubapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import thelaborseekers.jobhubapi.dto.EmpresaDTO;
import thelaborseekers.jobhubapi.model.entity.Empresa;

@Component
public class EmpresaMapper {
    private final ModelMapper modelMapper;
    public EmpresaMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public EmpresaDTO toDTO(Empresa empresa) {
        return modelMapper.map(empresa, EmpresaDTO.class);
    }
    public Empresa toEntity(EmpresaDTO empresaDTO) {
        return modelMapper.map(empresaDTO, Empresa.class);
    }
}
