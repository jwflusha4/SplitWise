package com.shaw.SplitWise1.Repository;

import com.shaw.SplitWise1.Model.ExpenseUser;
import com.shaw.SplitWise1.Model.GivingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser,Long> {

    @Query(value = "SELECT eu.* FROM groupp_expenses g JOIN expense_paid_by ep on g.expenses_id=ep.expense_id JOIN expense_user eu on ep.paid_by_id=eu.id", nativeQuery = true)
    List<ExpenseUser> findByGroupIdAndGetting(@Param("groupId") long groupId);

    @Query(value = "SELECT eu.* FROM groupp_expenses g JOIN expense_owedby ep on g.expenses_id=ep.expense_id JOIN expense_user eu on ep.owedby_id=eu.id", nativeQuery = true)
    List<ExpenseUser> findByGroupIdAndGiving(@Param("groupId") long groupId, @Param("status") GivingType status);


}
