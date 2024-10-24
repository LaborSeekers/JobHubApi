package thelaborseekers.jobhubapi.dto;

import java.util.List;

public class PostulanteHistorialDTO {
    private int cantidadPostulaciones;
    private List<PostulacionDTO> postulaciones;

  // Getters y Setters
  public int getCantidadPostulaciones() {
    return cantidadPostulaciones;
}

public void setCantidadPostulaciones(int cantidadPostulaciones) {
    this.cantidadPostulaciones = cantidadPostulaciones;
}

public List<PostulacionDTO> getPostulaciones() {
    return postulaciones;
}

public void setPostulaciones(List<PostulacionDTO> postulaciones) {
    this.postulaciones = postulaciones;
}
}
