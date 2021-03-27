package ru.vyazankin.market.ws.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.vyazankin.market.service.ProductService;
import ru.vyazankin.market.ws.products.GetAllProductsRequest;
import ru.vyazankin.market.ws.products.GetAllProductsResponse;
import ru.vyazankin.market.ws.products.GetProductByIdRequest;
import ru.vyazankin.market.ws.products.GetProductByIdResponse;


@Endpoint
@RequiredArgsConstructor

public class ProductsEndpoint {
    private static final String NAMESPACE_URI = "http://www.vyazankin.ru/market/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getStudentByName(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productService.getProductWSById(request.getId()));
        return response;
    }

    /*
        Пример запроса: POST http://localhost:8189/market/ws

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.vyazankin.ru/market/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllStudents(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        response.getProducts().addAll(productService.getAllProductsWS());
        return response;
    }
}
