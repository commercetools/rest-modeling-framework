<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

interface User extends Person
{
    const FIELD_ROLE = 'role';

    /**
     * @return string
     */
    public function getRole();

    /**
     * @param string $role
     * @return $this
     */
    public function setRole(string $role);

}
