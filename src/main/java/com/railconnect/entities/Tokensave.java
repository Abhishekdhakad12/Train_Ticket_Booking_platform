package com.railconnect.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Token")
public class Tokensave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tokenid;
	private String subject;
	private Date issueAt;
	private Date expiration;
	
//	@Column(length = 1000)
	private String token;

}
