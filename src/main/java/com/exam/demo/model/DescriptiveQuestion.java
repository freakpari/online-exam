package com.exam.demo.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)

public class DescriptiveQuestion  extends  Questions{
}
