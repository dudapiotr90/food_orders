//package pl.dudis.foodorders.infrastructure.database.security;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Getter
//@Setter
//@EqualsAndHashCode(of = "accountManagerId")
//@ToString(of = {"accountManagerId"})
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "account_manager")
//public class AccountManagerEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column
//    private Integer accountManagerId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name ="api_role_id")
//    private ApiRoleEntity apiRole;
//
//    @OneToOne
//    @JoinColumn(name = "account_id")
//    private AccountEntity account;
//}
