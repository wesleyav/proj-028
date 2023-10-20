package com.github.wesleyav.desafioapi.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	private Account account;

	@OneToOne(cascade = CascadeType.ALL)
	private Card card;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Feature> features;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<News> news;
}
