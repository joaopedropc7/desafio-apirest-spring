package br.com.api.capyba.controller;

import br.com.api.capyba.models.PostModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/terms")
public class TermsController {

    @GetMapping
    @Operation(summary = "Download terms of service in pdf", description = "Terms of service",
            tags = {"Terms"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PostModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<byte[]> gerarPDF() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();
            document.add(new Paragraph("TERMOS DE SERVIÇO\n" +
                    "\n" +
                    "1. Aceitação dos Termos\n" +
                    "\n" +
                    "Bem-vindo a Esphera. Ao acessar ou usar nossos serviços, você concorda em obedecer e estar vinculado a estes Termos de Serviço.\n" +
                    "\n" +
                    "2. Uso do Serviço\n" +
                    "\n" +
                    "Você concorda em usar o serviço fornecido pelo Site apenas para fins legais e de maneira que não viole estes Termos ou qualquer lei aplicável.\n" +
                    "\n" +
                    "3. Conteúdo do Usuário\n" +
                    "\n" +
                    "Ao fornecer qualquer conteúdo para o Site, você concede a nós uma licença mundial, não exclusiva, irrevogável, transferível e livre de royalties para usar, modificar, exibir, distribuir e reproduzir esse conteúdo.\n" +
                    "\n" +
                    "4. Privacidade\n" +
                    "\n" +
                    "Nosso uso das informações pessoais está sujeito à nossa Política de Privacidade, que pode ser encontrada [inserir link para a política de privacidade].\n" +
                    "\n" +
                    "5. Modificações nos Termos\n" +
                    "\n" +
                    "Reservamo-nos o direito de modificar estes Termos a qualquer momento. Notificaremos os usuários de quaisquer alterações significativas. O uso continuado do serviço após tais modificações constitui sua aceitação dos novos Termos.\n" +
                    "\n" +
                    "6. Limitação de Responsabilidade\n" +
                    "\n" +
                    "O Site não é responsável por quaisquer danos diretos, indiretos, incidentais, consequentes ou especiais resultantes do uso ou incapacidade de usar nossos serviços.\n" +
                    "\n" +
                    "7. Lei Aplicável\n" +
                    "\n" +
                    "Estes Termos são regidos pelas leis e você concorda com a jurisdição exclusiva dos tribunais dessa jurisdição.\n" +
                    "\n" +
                    "8. Contato\n" +
                    "\n" +
                    "Se você tiver alguma dúvida ou preocupação sobre estes Termos de Serviço, entre em contato conosco em espherasofthouse@gmail.com."));

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("Terms of service Esphera", "Terms of service Esphera.pdf");

            return new ResponseEntity<>(baos.toByteArray(), headers, org.springframework.http.HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}