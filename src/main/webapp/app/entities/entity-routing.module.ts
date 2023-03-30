import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'proba',
        data: { pageTitle: 'shopApp.proba.home.title' },
        loadChildren: () => import('./proba/proba.module').then(m => m.ProbaModule),
      },
      {
        path: 'cart',
        data: { pageTitle: 'shopApp.cart.home.title' },
        loadChildren: () => import('./cart/cart.module').then(m => m.CartModule),
      },
      {
        path: 'order-main',
        data: { pageTitle: 'shopApp.orderMain.home.title' },
        loadChildren: () => import('./order-main/order-main.module').then(m => m.OrderMainModule),
      },
      {
        path: 'payment',
        data: { pageTitle: 'shopApp.payment.home.title' },
        loadChildren: () => import('./payment/payment.module').then(m => m.PaymentModule),
      },
      {
        path: 'payment-cart',
        data: { pageTitle: 'shopApp.paymentCart.home.title' },
        loadChildren: () => import('./payment-cart/payment-cart.module').then(m => m.PaymentCartModule),
      },
      {
        path: 'payment-order-main',
        data: { pageTitle: 'shopApp.paymentOrderMain.home.title' },
        loadChildren: () => import('./payment-order-main/payment-order-main.module').then(m => m.PaymentOrderMainModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'shopApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'product-in-cart',
        data: { pageTitle: 'shopApp.productInCart.home.title' },
        loadChildren: () => import('./product-in-cart/product-in-cart.module').then(m => m.ProductInCartModule),
      },
      {
        path: 'product-in-order-main',
        data: { pageTitle: 'shopApp.productInOrderMain.home.title' },
        loadChildren: () => import('./product-in-order-main/product-in-order-main.module').then(m => m.ProductInOrderMainModule),
      },
      {
        path: 'shipment-cart',
        data: { pageTitle: 'shopApp.shipmentCart.home.title' },
        loadChildren: () => import('./shipment-cart/shipment-cart.module').then(m => m.ShipmentCartModule),
      },
      {
        path: 'shipment-order-main',
        data: { pageTitle: 'shopApp.shipmentOrderMain.home.title' },
        loadChildren: () => import('./shipment-order-main/shipment-order-main.module').then(m => m.ShipmentOrderMainModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
