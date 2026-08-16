package com.ecommerce.shop.models.entitys.products;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "brownies")
@DiscriminatorValue("BROWNIES")
public class Brownie extends Bakeable {
}
